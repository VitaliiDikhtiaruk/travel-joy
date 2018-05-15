package model;

import java.io.Serializable;

public class Model implements Serializable {

	private static final long serialVersionUID = -1708707490768225240L;

	private Long id;

	public Model() {

	}

	public Model(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
