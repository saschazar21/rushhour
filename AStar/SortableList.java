package AStar;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("rawtypes")
public class SortableList<T extends Comparable> {
	
	private ArrayList<T> list = new ArrayList<T>();
	private boolean needsSorting = false;
	
	public void add(T element) {
		this.needsSorting = true;
		this.list.add(element);
	}
	
	
	public int indexOf(T element) {
		return this.list.indexOf(element);
	}
	
	public T get(int index) {
		return this.list.get(index);
	}
	
	public T get(T element) {
		int index = this.list.indexOf(element);
		
		if (index == -1) {
			return null;
		}
		
		return this.list.get(index);
	}
	
	public T remove(int index) {
		T removed = this.list.remove(index);
		
		if (removed != null) {
			this.needsSorting = true;
		}
		
		return removed;
		
	}
	
	public boolean remove(T element) {
		boolean removed = this.list.remove(element);
		
		if (removed == true) {
			this.needsSorting = true;
		}
		
		return removed;
	}
	
	public void clear() {
		this.list.clear();
		this.needsSorting = false;
	}
	
	public boolean contains(T element) {
		return this.list.contains(element);
	}
	
	public int size() {
		return this.list.size();
	}
	
	public boolean isEmpty() {
		return this.list.isEmpty();
	}
	
	public boolean needsSorting() {
		return this.needsSorting;
	}
	
	@SuppressWarnings("unchecked")
	public void sort() {
		if (this.needsSorting) {
			Collections.sort(this.list);
			this.needsSorting = false;
		}
	}

}
