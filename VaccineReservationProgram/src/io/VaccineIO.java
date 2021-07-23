package io;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import controller.VaccineManager;
import model.vo.Hospital;
import model.vo.Member;

/*
 *  @author JISUJIN
 */

public class VaccineIO {
	Hospital hp = new Hospital();
	
	public ArrayList<Member> memberList() {
		ArrayList<Member> members = new ArrayList<>();
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservation.txt"))){
			
			while(true) {
				Member member = (Member) ois.readObject();
				members.add(member);
			}
			
		} catch (EOFException e) {
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			return null;		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return members;
	}

	public void memberSave() {
		memberSave (new VaccineManager().memberList());
	}
	
	public void memberSave(Map<String, Member> someMap) { // 예약 명단 출력
		
		ArrayList<Member> memberList = new ArrayList<Member>(someMap.values());

		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservation.txt"))){

			
			for(int i = 0; i < memberList.size(); i++) {
				oos.writeObject(memberList.get(i));
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void memberRead() {
		ArrayList<Member> members = new ArrayList<>();
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservation.txt"))){
			while(true) {
				Member m = (Member) ois.readObject();
				members.add(m);
			}
		} catch(EOFException e) {
			members.sort(null);
			
			int indexNum = 0;
			for(int i = 0; i < members.size(); i++) {
				System.out.println((++indexNum)+". " + members.get(i));
			}
			
			System.out.printf("%n※ 총 %d명이 예약되어 있습니다.%n", members.size());
			
			return;			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	public void memberRead(Map<String, Member> someMap) { // 예약 명단 입력
	
		ArrayList<Member> memberList = new ArrayList<Member>(someMap.values());
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservation.txt"))){
		
			int i = 0;
			
			
			while(true) {
				
				memberList.add((Member)ois.readObject());
				
				System.out.println(memberList.get(i));
				i++;
			}
	
		} catch(EOFException e) {
			return;			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // 닫아주기
			
		}
		
		// 파일의 한 줄 한 줄 읽어서 출력한다.
		for(int i=0;i<memberList.size();i++) {
			System.out.println(memberList.get(i));
		}
	}
	
	public Hospital vaccineSave(int cnt) { // 백신 현황 출력
		hp.setVaccineCount(cnt);
		
		try(
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("vaccine.txt"))
		){
			oos.writeObject(hp);
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return hp;
	}
	
	public void vaccineSave() { 
	
		try(
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("vaccine.txt"))
		){
			oos.writeObject(hp);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public Hospital vaccineRead() { // 백신 현황 입력
		Hospital h = new Hospital();
		try(
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("vaccine.txt"))
		){
			h = (Hospital) ois.readObject();
			
		} catch (FileNotFoundException e) {
			return h;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		  catch (IOException e) {
			e.printStackTrace();
		} 
		return h;
	}
	
}