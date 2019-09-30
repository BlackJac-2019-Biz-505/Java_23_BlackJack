package com.biz.blackjack.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BlackjackService {
	
	Map<String, Integer> bjMap;
	Map<String, Integer> dealerMap;
	Map<String, Integer> playerMap;
	
	Random rnd;
	Scanner scan;
	
	Set<String> tempSet;
	List<String> tempList;
	
	int sumDealer;
	int sumPlayer;
	
	public BlackjackService() {
		bjMap = new HashMap<>(); // Map은 인덱스가 아닌 key로 가져올 것이기 때문에 성능을 위해 HashMap으로 생성
		dealerMap = new HashMap<>();
		playerMap = new HashMap<>();
		rnd = new Random();
		scan = new Scanner(System.in);
	}
	
//	카드를 만들고 블랙잭 맵에 저장하는 메소드
	public void makeCard () {
		for(int i = 1 ; i <= 13 ; ++i) {			
			String num = i + "";
			
			if(i == 1) num = "A";
			else if(i == 11) num = "J";
			else if(i == 12) num = "Q";
			else if(i == 13) num = "K";
			
			String spade = "Spade " + num;
			String diamond = "Diamond " + num;
			String heart = "Heart "+ num;
			String clover = "Clover "+ num;
			
			if(i <= 10) {
				bjMap.put(spade, i);
				bjMap.put(diamond, i);
				bjMap.put(heart, i);
				bjMap.put(clover, i);
			} else if (i > 10) {
				bjMap.put(spade, 10);
				bjMap.put(diamond, 10);
				bjMap.put(heart, 10);
				bjMap.put(clover, 10);
			}
		}
		
		tempSet = new HashSet<String>(bjMap.keySet()); // key를 셔플하기 위해 Set으로 변환
		tempList = new ArrayList<>(tempSet); // Collections.shuffle을 위해선 List 타입이 들어가야 하기 때문에 Set을 List로 변환, 추가/삭제가 아니기 때문에 ArrayList 사용
		Collections.shuffle(tempList); // bjMap의 keySet을 List로 변환하여 순서를 무작위로 변경
	}
	
//	딜러,플레이어가 카드를 뽑는 메소드
	public void cardDraw () {
		
//		bjMap의 key와 value를 딜러,플레이어,딜러,플레이어 순서로 할당
		
		String key;
		int index = 0;
		
		for(index = 0 ; index < 4 ; ++index) {
			if(index % 2 == 0) {
				key = tempList.get(index); // 셔플된 List의 첫번째 String형 key를 가져옴
				dealerMap.put(key, bjMap.get(key)); // 그 key를 기준으로 bjMap의 key와 value를 가져오고 dealerMap에 집어넣음
				sumDealer += dealerMap.get(key); // dealerMap의 value합을 변수에 저장
				System.out.println("딜러가 " + key + "를 뽑았습니다. 딜러 점수 합:" + sumDealer);
				bjMap.remove(key); // 혹시 나중에 추가로 카드를 뽑는 코드를 만들 때 bjMap을 사용할 경우 중복카드를 뽑지 않도록 bjMap에서 key 삭제
			} else {
				key = tempList.get(index); // i가 홀수일 때는 playerMap에 추가
				playerMap.put(key, bjMap.get(key));
				sumPlayer += playerMap.get(key);
				System.out.println("플레이어가 " + key + "를 뽑았습니다. 플레이어 점수 합:" + sumPlayer);
				bjMap.remove(key);
			}
		}
		
//		플레이어 카드 뽑기
		while(true) {
			if(sumPlayer > 21) return; // 플레이어의 카드 합이 21을 초과하면 return
			System.out.println("\n[카드를 더 뽑으시겠습니까? (y/n)]");
			System.out.print(">");
			String strScan = scan.nextLine();
			if( strScan.equalsIgnoreCase("n") ) break; // "n"이면 break
			else if( strScan.equalsIgnoreCase("y") ){ // "y"면 진행
				key = tempList.get(index++); // tempList의 index번째 문자열 값을 key에 저장하고 index를 1 추가
				playerMap.put(key, bjMap.get(key)); // player맵에 key값과 bjMap에서 해당하는 value를 추가
				sumPlayer += playerMap.get(key); // sumPlayer변수에 key에 해당하는 value를 추가
				System.out.println("플레이어가 " + key + "를 뽑았습니다. 플레이어 점수 합:" + sumPlayer);
				bjMap.remove(key);
			}
		}
		
//		딜러가 16 이하일 경우
		while (sumDealer < 17) {
			key = tempList.get(index++); // tempList의 index번째 문자열 값을 key에 저장하고 index를 1 추가
			dealerMap.put(key, bjMap.get(key)); // dealer맵에 key값과 bjMap에서 해당하는 value를 추가
			sumDealer += dealerMap.get(key); // sumDealer변수에 key에 해당하는 value를 추가
			System.out.println("딜러가 " + key + "를 뽑았습니다. 딜러 점수 합:" + sumDealer);
			bjMap.remove(key);
		}
		
	}
	
	public void result() {
		if (sumPlayer > 21) {
			System.out.println("=============================================");
			System.out.println("플레이어 버스트로 패배!");
			System.out.println("=============================================");
		} else if (sumDealer > 21) {
			System.out.println("=============================================");
			System.out.println("딜러 버스트로 플레이어 승리!");
			System.out.println("=============================================");
		} else if (sumPlayer == sumDealer) {
			System.out.println("=============================================");
			System.out.println("동점으로 무승부!");
			System.out.println("=============================================");
		} else if (sumPlayer > sumDealer) {
			System.out.println("=============================================");
			System.out.println("플레이어 승리!");
			System.out.println("=============================================");
		} else if (sumDealer > sumPlayer) {
			System.out.println("=============================================");
			System.out.println("딜러 승리!");
			System.out.println("=============================================");
		}
		System.out.println("플레이어 점수 합:" + sumPlayer);
		System.out.println("딜러 점수 합:" + sumDealer);
		
	}

}
