package tools;

import tools.Observer;

//https://docs.oracle.com/javase/7/docs/api/java/util/Observable.html
public interface Observable {
	public void addObserver(Observer o);
	public void deleteObserver(Observer o);
	public void notifyObservers(Object o);
}
