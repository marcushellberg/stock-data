import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import router from 'Frontend/routes.js';

const root = createRoot(document.getElementById('outlet')!);
root.render(<RouterProvider router={router} />);
