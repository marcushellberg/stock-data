import { ComboBox } from '@hilla/react-components/ComboBox.js';
import { Grid } from '@hilla/react-components/Grid.js';
import { GridColumn } from '@hilla/react-components/GridColumn.js';
import { useEffect, useState } from 'react';
import StockPrice from 'Frontend/generated/org/vaadin/marcus/astra/data/StockPrice.js';
import StockSymbol from 'Frontend/generated/org/vaadin/marcus/astra/data/StockSymbol.js';
import { StockPriceEndpoint } from 'Frontend/generated/endpoints.js';

export function DetailsView() {
  const [stockData, setStockData] = useState<StockPrice[]>([]);
  const [symbols, setSymbols] = useState<StockSymbol[]>();

  useEffect(() => {
    StockPriceEndpoint.getSymbols().then(setSymbols);
  }, []);

  async function getDataFor(ticker?: string) {
    if (ticker) setStockData(await StockPriceEndpoint.findAllByTicker(ticker));
  }

  return (
    <div className="p-m flex flex-col gap-m h-full box-border">
      <h1>Browse historical data (Cassandra)</h1>
      <ComboBox
        label="Company"
        items={symbols}
        style={{ width: '50%' }}
        itemLabelPath="name"
        onSelectedItemChanged={(e) => getDataFor(e.detail.value?.symbol)}
      />

      <Grid items={stockData} className="flex-grow">
        <GridColumn path="time" />
        <GridColumn path="open" />
        <GridColumn path="high" />
        <GridColumn path="low" />
        <GridColumn path="close" />
        <GridColumn path="volume" />
      </Grid>
    </div>
  );
}
