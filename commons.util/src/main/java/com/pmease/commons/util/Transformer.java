package com.pmease.commons.util;

import java.io.Serializable;

public interface Transformer<T> extends Serializable {
	T transform(T text);
}
