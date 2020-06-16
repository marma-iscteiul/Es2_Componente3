package Ponto4.ProjectES;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
	{
		System.out.println( "Hello World!" );
		GetDataFromGit gdfg = new GetDataFromGit();
		try {
			gdfg.GitRepository();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}