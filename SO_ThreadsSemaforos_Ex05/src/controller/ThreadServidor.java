package controller;

import java.util.concurrent.Semaphore;

public class ThreadServidor extends Thread{
	private Semaphore semaforo;
	private int id;
	private double tempoCalc;
	private double tempoBd;
	
	public ThreadServidor(int id, Semaphore semaforo) {
		this.id=id;
		this.semaforo=semaforo;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int resto=id%3;
		switch (resto) {
		case 1: resto1(); break; 
		case 2: resto2(); break;
		case 0: resto0(); break;
		}
	}

	private void resto0() {
		// TODO Auto-generated method stub
		tempoBd=1500;
		for(int i=0;i<3;i++) {
			tempoCalc=tempoEspera(1100,900);
			calculos();
			transacaoBd();
		}
	}

	private void resto2() {
		// TODO Auto-generated method stub
		tempoBd=1500;
		for(int i=0;i<3;i++) {
			tempoCalc=tempoEspera(1101,399);
			calculos();
			transacaoBd();
		}
	}

	private void resto1() {
		// TODO Auto-generated method stub
		tempoBd=1000;
		for(int i=0;i<2;i++) {
			tempoCalc=tempoEspera(900,101);
			calculos();
			transacaoBd();
		}
	}
	
	private int tempoEspera(int mult, int soma) {
		// TODO Auto-generated method stub
		int tempo=(int)(Math.random()*mult)+soma;
		return tempo;
	}
	
	private void calculos() {
		// TODO Auto-generated method stub
		dormir(tempoCalc);
		imprimir("calculo");
	}

	private void transacaoBd() {
		// TODO Auto-generated method stub
		try {
			semaforo.acquire();
			dormir(tempoBd);
			imprimir("transacao");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			semaforo.release();
		}
	}
	
	private void dormir(double tempo) {
		// TODO Auto-generated method stub
		try {
			sleep((int)tempo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void imprimir(String string) {
		// TODO Auto-generated method stub
		if(string.equals("calculo")) {
			System.out.println("Calculo do id:"+id+"; tempo: "+tempoCalc/1000+"s");
		}else {
			System.out.println("Transacao no banco de dados do id:"+id+"; tempo: "+tempoCalc/1000+"s");
		}
	}
}
