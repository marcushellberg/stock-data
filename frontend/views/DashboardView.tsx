import { ComboBox } from '@hilla/react-components/ComboBox.js';
import ReactApexChart from 'react-apexcharts';
import { ApexOptions } from 'apexcharts';
import { useEffect, useState } from 'react';
import { StockPriceEndpoint } from 'Frontend/generated/endpoints.js';
import StockSymbol from 'Frontend/generated/org/vaadin/marcus/astra/data/StockSymbol.js';

export function DashboardView() {
  const [ticker, setTicker] = useState('');
  const [symbols, setSymbols] = useState<StockSymbol[]>();
  const [series, setSeries] = useState<ApexAxisChartSeries>([]);

  const options: ApexOptions = {
    chart: {
      type: 'candlestick',
      height: 350,
    },
    title: {
      text: `${ticker || 'Stock'} data`,
      align: 'left',
    },
    xaxis: {
      type: 'datetime',
    },
    yaxis: {
      tooltip: {
        enabled: true,
      },
    },
  };

  useEffect(() => {
    StockPriceEndpoint.getSymbols().then(setSymbols);
  }, []);

  useEffect(() => {
    const subscription = StockPriceEndpoint.getStockPriceStream().onNext((stockPrice) => {
      //@ts-ignore
      setSeries((prevState) => {
        const data = [
          ...((prevState[0] && prevState[0].data) || []),
          {
            x: new Date(stockPrice.time),
            y: [stockPrice.open, stockPrice.high, stockPrice.low, stockPrice.close],
          },
        ];

        return [{ data }];
      });
    });
    // Remember to close web socket when it's no longer needed
    return () => subscription.cancel();
  }, []);

  function tickerSelected(ticker?: string) {
    if (ticker) {
      setTicker(ticker);
      setSeries([]);
      StockPriceEndpoint.produceDataForTicker(ticker);
    }
  }

  return (
    <div className="p-m flex flex-col gap-m">
      <h1>Streaming data dashboard (Pulsar)</h1>
      <ComboBox
        label="Company"
        items={symbols}
        style={{ width: '50%' }}
        itemLabelPath="name"
        onSelectedItemChanged={(e) => tickerSelected(e.detail.value?.symbol)}
      />

      {/* ApexCharts does not properly support React 18 yet */}
      {/*@ts-ignore*/}
      <ReactApexChart type="candlestick" options={options} series={series} height={350} />
    </div>
  );
}
