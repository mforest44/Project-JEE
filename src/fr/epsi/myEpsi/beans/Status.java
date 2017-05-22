package fr.epsi.myEpsi.beans;

import java.util.ArrayList;

public enum Status {

	PUBLIC, PRIVATE, ARCHIVED;
	
	   private static Status[] allValues = values();
	   public static Status fromOrdinal(int n) {return allValues[n];}
	   public static ArrayList<Status> getList() { 
		   ArrayList listStatus = new ArrayList();
		   listStatus.add(PRIVATE);
		   listStatus.add(PUBLIC);
		   listStatus.add(ARCHIVED);
		   return listStatus;
	   }
}
