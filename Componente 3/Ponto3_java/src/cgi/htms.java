package cgi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.StringUtils;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.metadata.model.DocumentMetadata;

public class htms {

	private static ArrayList<String> autoresaux = new ArrayList<>();
	private static ArrayList<String> autores;
	private static File diretoria;

	public static void main(String[] args) throws  IOException, AnalysisException, InvalidRemoteException, TransportException, GitAPIException {
		autores = new ArrayList<String>();
		System.out.println("Content-type: text/html\n\n");
		clonar();
		criartabela();
		Meta();
		System.out.println("</table>");
		System.out.println( "</body>\n</html>\n");
	}

	/**
	 * Metodo para clonar o o repositorio github que possui os documentos pdf necessários.
	*/
	public static void clonar() {
		try {
			String URI = "https://github.com/marma-iscteiul/covid19repositories.git";
			diretoria = new File("./clonegit");

			deleteDir(diretoria);

			Git git;
			git = Git.cloneRepository()
					.setURI(URI)
					.setDirectory(diretoria)
					.call();
			Repository repository = git.getRepository();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	/**
	 * Imprime o codigo html correspondente à criacao da tabela
	*/
	public static void criartabela() {
		System.out.println( "<html>\r\n" + 
				"<head>\r\n" + 
				"<style>\r\n" + 
				"#customers {\r\n" + 
				"  font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\r\n" + 
				"  border-collapse: collapse;\r\n" + 
				"  width: 100%;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"h1 {text-align: center;\r\n" + 
				"font-family:arial;\r\n" + 
				"color:#830012\r\n" + 
				"}\r\n" + 
				"#customers td, #customers th {\r\n" + 
				"  border: 1px solid #ddd;\r\n" + 
				"  padding: 8px;\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"#customers tr:nth-child(even){background-color: #f2f2f2;}\r\n" + 
				"\r\n" + 
				"#customers tr:hover {background-color: #ddd;}\r\n" + 
				"\r\n" + 
				"#customers th {\r\n" + 
				"  padding-top: 12px;\r\n" + 
				"  padding-bottom: 12px;\r\n" + 
				"  text-align: left;\r\n" + 
				"  background-color: #830012;\r\n" + 
				"  color: white;\r\n" + 
				"}\r\n" + 
				"</style>\r\n" + 
				"</head>\r\n" + 
				"<title> Artigos Covid19</title> \r\n" + 
				"<body>\r\n" + 
				"<h1>Artigos Covid19</h1>\r\n" + 
				"<table id=\"customers\">\r\n" + 
				"  <tr> \r\n" + 
				"    <th>Article title</th>\r\n" + 
				"    <th> Journal name</th>\r\n" + 
				"    <th>Publication year</th>\r\n" + 
				"    <th> Authors</th>\r\n" + 
				"</tr>" ) ;
	}
	
	
	
	/**
	 * Método que extrai a metadata e imprime o codigo html correspondente
	*/
	public static void Meta() {
		int v=0;
		if (diretoria.isDirectory()) {
			for (File f : diretoria.listFiles()) {
				if (f.isFile() && f.getName().endsWith(".pdf")) {
					try {
						ContentExtractor extractor = new ContentExtractor();
						InputStream inputStream = new FileInputStream(diretoria+ "/"+f.getName());
						extractor.setPDF(inputStream);
						DocumentMetadata doc = extractor.getMetadata();
						String titulo = doc.getTitle();
						String nome = doc.getJournal();
						DateType dt=DateType.PUBLISHED;
						String ano =doc.getDate(dt).getYear();
						List<DocumentAuthor> autor = doc.getAuthors();

						System.out.println("<tr>");
						System.out.println("<td> <a href=\"https://github.com/marma-iscteiul/covid19repositories/blob/master/" + f.getName() + "\"" + ">" + titulo + "</a>" + "</td>" );
						System.out.println("<td>" + nome + "</td>" );
						System.out.println("<td>" + ano + "</td>" );

						for ( int i=0; i!=autor.size(); i++) {
							autores.add(autor.get(i).getName());	
						}

						String authors = StringUtils.join(autores, ", ");
						autores.clear();
						autoresaux.add(authors);

						System.out.println("<td>" + autoresaux.get(v) +  "</td>" );
						v++;
						System.out.println("</tr>");
					} catch (IOException | AnalysisException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	/**
	 * Função que apaga a diretoria criada pelo clone.
	*/
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i=0; i<children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete(); 
	}

	
	
	
	/**
	 * Função que devolve a lista de autores auxiliar.
	*/
	public static ArrayList<String> getAutoresaux() {
		return autoresaux;
	}

	/**
	 * Função que define a lista auxiliar de autores.
	*/
	public static void setAutoresaux(ArrayList<String> autoresaux) {
		htms.autoresaux = autoresaux;
	}

	
	/**
	 * Função que devolve a lista de autores.
	*/
	public static ArrayList<String> getAutores() {
		return autores;
	}

	/**
	 * Função que define a lista de autores.
	*/
	public static void setAutores(ArrayList<String> autores) {
		htms.autores = autores;
	}

	
	/**
	 * Função que define o atributo diretoria.
	*/
	public static void setDiretoria(File diretoria) {
		htms.diretoria = diretoria;
	}

	/**
	 * Função que devolve o atributo diretoria.
	*/
	public static File getDiretoria() {
		return diretoria;
	}


}
