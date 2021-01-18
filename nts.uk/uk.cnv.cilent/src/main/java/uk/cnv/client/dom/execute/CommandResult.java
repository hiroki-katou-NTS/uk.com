package uk.cnv.client.dom.execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.cnv.client.LogManager;

public class CommandResult {
	private static final String LINE_SEPA = "\r\n";

	private StringBuffer out;
	private StringBuffer err;
	private int exitValue;

	public CommandResult() {
		this.out = new StringBuffer();
		this.err = new StringBuffer();
		this.exitValue = 0;
	}

	public CommandResult(String out, String err, int exitValue) {
		this.out.append(out);
		this.err.append(err);
		this.exitValue = exitValue;
	}

	public CommandResult(Exception e) {
		this("", e.getMessage(), -1);
	}

//	public CommandResult merge(CommandResult other) {
//		return new CommandResult(
//			this.out.toString() + LINE_SEPA + other.out.toString(),
//			this.err.toString() + LINE_SEPA + other.err.toString(),
//			other.isError() ? other.exitValue : this.exitValue);
//	}

	public CommandResult(Process p) {
		this();
		if(p == null) {
			this.exitValue = -1;
			return;
		}

		InputStream in = null;
		BufferedReader br = null;
		try {
			in = p.getInputStream();
			this.out = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(in, "Shift_JIS"));
			String line;
			while ((line = br.readLine()) != null) {
				out.append(line + LINE_SEPA);
			}
			br.close();
			in.close();

			in = p.getErrorStream();
			this.err = new StringBuffer();
			br = new BufferedReader(new InputStreamReader(in, "Shift_JIS"));
			while ((line = br.readLine()) != null) {
				err.append(line + LINE_SEPA);
			}
			this.exitValue = p.exitValue();
		} catch (IOException e) {
			LogManager.err(e);
		} finally {
			try {
				if (br != null) br.close();
				if (in != null) in.close();
			} catch (IOException e) {
				LogManager.err(e);
			}
		}
	}

	public void log() {
		LogManager.out(this.out.toString());
		LogManager.err(this.err.toString());

		this.out = new StringBuffer();
		this.err = new StringBuffer();
	}

	public boolean isError() {
		return (this.exitValue != 0);
	}
}