package de.pennychecker.kata.converter;


public interface Converter<T,C> {
	public C convert(T t);
}
