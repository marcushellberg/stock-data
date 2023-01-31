import { AppLayout } from '@hilla/react-components/AppLayout.js';
import { DrawerToggle } from '@hilla/react-components/DrawerToggle.js';
import { NavLink, Outlet } from 'react-router-dom';

export default function MenuOnLeftLayout() {
  return (
    <AppLayout className="block h-full" primarySection="drawer">
      <DrawerToggle slot="navbar" />

      <div slot="drawer" className="flex flex-col gap-m p-m">
        <h1 className="text-l m-0">Stock Data</h1>
        <nav>
          <ul className="list-none p-0">
            <li>
              <NavLink to="/">Dashboard</NavLink>
            </li>
            <li>
              <NavLink to="/details">Details</NavLink>
            </li>
          </ul>
        </nav>
      </div>

      <Outlet />
    </AppLayout>
  );
}
