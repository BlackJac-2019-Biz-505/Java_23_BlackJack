package com.biz.blackjack.exec;

import java.util.Scanner;

import com.biz.blackjack.service.BlackjackService;

public class BlackjackEx {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		BlackjackService bs = new BlackjackService();
		bs.makeCard();
		bs.cardDraw();

		bs.result();

		while (true) {
			System.out.println("\n[새로운 게임을 하시겠습니까? (y/n)]");
			System.out.print(">");
			String strScan = scan.nextLine();
			if (strScan.equalsIgnoreCase("y")) {
				bs = new BlackjackService();
				bs.makeCard();
				bs.cardDraw();

				bs.result();
			} else if (strScan.equalsIgnoreCase("n")) {
				System.out.println("게임을 종료합니다.");
				break;
			}
		}

		scan.close();
	}

}
