package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	//아이템 추가
	public static void createItem(TodoList list) {
		String title, desc, ctg, due_date;
		Scanner sc = new Scanner(System.in);

		System.out.print("\n" + "========== [항목 추가] \n" + "카테고리 > ");
		ctg = sc.next();

		System.out.print("제목 > ");
		title = sc.next();
		sc.nextLine(); // 제목 뒤에 들어오는 enter 제거
		
		if(list.isDuplicate(title)) {
			System.out.println("중복된 제목입니다.");
			return;
		}

		System.out.print("내용 > ");
		desc = sc.nextLine().trim(); // trim-> 좌우여백 제거

		System.out.print("기한 (yyyy/mm/dd) > ");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, ctg, due_date);
		if (list.addItem(t) > 0)
			System.out.println("아이템 추가 완료 !!");
	}
	//아이템 삭제 
	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);

		System.out.print("\n" + "========== [항목 삭제]\n" + "삭제할 항목의 번호 입력 > ");
		int index = sc.nextInt();

		if (l.deleteItem(index) > 0)
			System.out.println("아이템 삭제 완료 !!");
	}
	//아이템 수정 
	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.print("\n" + "========== [항목 수정]\n" + "수정할 항목의 번호 입력 > ");

		int index = sc.nextInt();

		System.out.print("새 카테고리 > ");
		String ctg = sc.next();
		System.out.print("새 제목 > ");
		String title = sc.next();
		sc.nextLine();
		
		if(l.isDuplicate(title)) {
			System.out.println("중복된 제목입니다.");
			return;
		}
		
		System.out.print("새 내용 > ");
		String desc = sc.nextLine().trim();
		System.out.print("새 기한 (yyyy/mm/dd) > ");
		String due_date = sc.next();

		TodoItem t = new TodoItem(index, title, desc, ctg, due_date);
		if (l.updateItem(t) > 0)
			System.out.println("아이템 수정 완료 !!");

	}
	//아이템 완료 
	public static void completeItem(TodoList l, int index) {
		if(l.completeItem(index) > 0)
			System.out.println("아이템 체크 완료 !!");
	}
	//전체 출력 
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("\n" + "========== [전체 목록, 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	//완료된 것만 출력 
	public static void listAll(TodoList l, int comp) {
		int count =0;
		for(TodoItem t: l.getList(comp)) {
			System.out.println(t.toString());
			count++;
		}
		if (count == 0)
			System.out.println("완료된 항목이 없습니다.");
		else
			System.out.println("총 " + count + "개의 항목이 완료되었습니다.");
		
	}
	/*
	public static void saveList(TodoList l, String filename) { // 프로그램 종료 시 저장
		// FileWriter
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem m : l.getList()) {
				w.write(m.toSaveString());
			}
			w.close();
			System.out.println("TodoList 저장 완료 !!\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void loadList(TodoList l, String filename) { // 프로그램 시작 시 읽기
		// BufferedReader, FileReader, StringTokenizer
		int num = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

			String oneline;
			while ((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				String ctg = st.nextToken();
				String d_date = st.nextToken();
				TodoItem m = new TodoItem(title, desc, date, ctg, d_date);
				l.addItem(m);
				num++;
			}
			br.close();
			System.out.println(num + "개의 항목 로딩 완료 !!");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("todolist.txt 파일이 없습니다.\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} */
	//find keyword 
	public static void findKeyword(TodoList l, String find) {
		int count = 0;
		for (TodoItem t : l.getList(find)) {
			System.out.println(t.toString());
			count++;
		}
		if (count == 0)
			System.out.println("해당 키워드를 포함하는 항목이 없습니다.");
		else
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	//find_cate keyword 
	public static void findCtg(TodoList l, String find) {
		int count = 0;
		for (TodoItem t : l.getCategory(find)) {
			count++;
			System.out.println(t.toString());
		}
		if (count == 0)
			System.out.println("해당 키워드를 포함하는 항목이 없습니다.");
		else
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	// list all categories 
	public static void listCate(TodoList l) {
		int count = 0;
		for (String t : l.getCategories()) {
			count++;
			System.out.print(t +" ");
		}
		if (count == 0)
			System.out.println("\n해당 키워드를 포함하는 항목이 없습니다.");
		else
			System.out.println("\n총 " + count + "개의 카테고리가 등록되어 있습니다.");
	}
	
}
