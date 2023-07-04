import { createBrowserRouter } from 'react-router-dom';
import { PokeCompView } from './views/PokeCompView';

const router = createBrowserRouter([
  { path: '/', element: <PokeCompView /> },
]);

export default router;
