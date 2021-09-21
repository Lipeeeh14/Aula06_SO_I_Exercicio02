package controller;

import java.util.concurrent.Semaphore;

public class ThreadBancoController extends Thread {
	
	private Semaphore deposito, saque;
	private int tipo;
	private int codConta;
	private double saldoConta;
	private double valorTransacao;
	
	public ThreadBancoController(Semaphore saque, Semaphore deposito, int tipo, int codConta, double saldoConta, double valorTransacao) {
		this.deposito = deposito;
		this.saque = saque;
		this.tipo = tipo;
		this.codConta = codConta;
		this.saldoConta = saldoConta;
		this.valorTransacao = valorTransacao;
	}	
	
	@Override
	public void run() {
		transacao();
	}

	private void transacao() {
		if (tipo == 0) {
			try {
				deposito.acquire();
				Thread.sleep(1000);
				System.out.println("Saldo da conta antes do depósito " + codConta + " R$ " + saldoConta);
				saldoConta += valorTransacao;
				System.out.println("Saldo da conta da após o depósito " + codConta + " R$ " + saldoConta);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				deposito.release();
			}
		} else {
			try {
				saque.acquire();
				Thread.sleep(1000);
				if (saldoConta < valorTransacao)
					System.err.println("Saldo negativo!");
				else {
					System.out.println("Saldo da conta antes do saque " + codConta + " R$ " + saldoConta);
					saldoConta -= valorTransacao;
					System.out.println("Saldo da conta da após o saque " + codConta + " R$ " + saldoConta);
				}					
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				saque.release();
			}
		}
	}
}
