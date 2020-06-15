package Testes;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import cgi.htms;

public class Junit {

	private static htms h = new htms();
	
	ArrayList lista = new ArrayList<>();

	@Test
	public void testClonar() {
		h.clonar();
	}

	@Test
	public void testCriartabela() {
		h.criartabela();
	}

	@Test
	public void testMeta() {
		h.Meta();
	}


	@Test
	public void testGetAutoresaux() {
		h.getAutoresaux();
	}

	@Test
	public void testSetAutoresaux() {
		h.setAutoresaux(lista);
	}

	@Test
	public void testGetAutores() {
		h.getAutores();
	}

	@Test
	public void testSetAutores() {
		h.setAutores(lista);
	}

	@Test
	public void testSetDiretoria() {
		h.setDiretoria(new File("./aaa"));
	}

	@Test
	public void testGetDiretoria() {
		h.getDiretoria();
	}

}
