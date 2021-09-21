package view;

import java.util.Random;
import java.util.concurrent.Semaphore;

import controller.ThreadBancoController;

public class Main {

	public static Semaphore saque;
	public static Semaphore deposito;
	
	public static void main(String[] args) {
		saque = new Semaphore(1);
		deposito = new Semaphore(1);
		Random dinheiro = new Random();
		int saldo, valor;
		
		for (int i = 0; i < 20; i++) {
			saldo = dinheiro.nextInt(2000) + 500;
			valor = dinheiro.nextInt(300) + 50;
			
			Thread banco = new ThreadBancoController(saque, deposito, i % 2, i, saldo, valor);
			banco.start();
		}
	}

}
