import { Server } from 'ws';
import { createServer } from 'http';
import Roulette from './roulette';

const wss = new Server({ port: 4433 });
const mm = new Roulette();

wss.on('connection', ws => {
  console.log('conectado');
  mm.register(ws);
});