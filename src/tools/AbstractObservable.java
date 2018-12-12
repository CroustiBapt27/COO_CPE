package tools;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import tools.Observer;

public abstract class AbstractObservable implements Observable {

	private List<Observer> observer = new LinkedList<Observer>();
	
	@Override
	public void addObserver(Observer obs) {
		this.observer.add(obs);
	}
	@Override
	public void deleteObserver(Observer obs) {
		this.observer.remove(obs);
	}
	@Override
	public void notifyObservers(Object obj) {
		/* for(Observer obs : observer) {
			if(obs != null) {
				obs.update(obj);
			}
		}
		*/ 
		
		Iterator<Observer> iterator = observer.listIterator();
		Observer obs;
		
		if(iterator != null) {
			while(iterator.hasNext()){
				obs = iterator.next();
				if(obs != null) {
					obs.update(obj);
				}
			}
		}
	}
}
