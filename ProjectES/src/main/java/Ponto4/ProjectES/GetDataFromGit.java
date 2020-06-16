package Ponto4.ProjectES;


import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;


import org.eclipse.jgit.lib.Repository;



public class GetDataFromGit {


	public void GitRepository() throws GitAPIException, IOException {

		Git git;	
		File file =  new File
				("C:\\Users\\joaov\\Desktop\\esssss");

		if(file.isDirectory() && !file.exists())
			git = Git.cloneRepository().setURI("https://github.com/vbasto-iscte/ESII1920.git").setDirectory(file).call();
		else {
			git = Git.open(new File("C:\\Users\\joaov\\Desktop\\esssss"));
			git.pull();
			git.checkout();
		}

		Repository repository = git.getRepository();


	}

	public static void main(String[] args) {
		GetDataFromGit gdfg = new GetDataFromGit();

		try {
			gdfg.GitRepository();
		} catch (GitAPIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
