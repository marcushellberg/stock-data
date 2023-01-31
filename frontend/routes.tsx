import MainLayout from 'Frontend/views/MainLayout.js';
import { createBrowserRouter } from 'react-router-dom';
import { DashboardView } from 'Frontend/views/DashboardView.js';
import { DetailsView } from 'Frontend/views/DetailsView.js';

const router = createBrowserRouter([
  {
    element: <MainLayout />,
    children: [
      { path: '/', element: <DashboardView /> },
      { path: '/details', element: <DetailsView /> },
    ],
  },
]);
export default router;
