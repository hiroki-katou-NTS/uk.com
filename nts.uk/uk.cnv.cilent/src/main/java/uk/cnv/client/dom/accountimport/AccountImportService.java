package uk.cnv.client.dom.accountimport;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import nts.gul.security.hash.password.PasswordHash;
import uk.cnv.client.LogManager;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmKihon;

public class AccountImportService {

	public boolean doWork(Require require) {
		LogManager.out("アカウントの移行 -- 処理開始 --");

		List<JinKaisyaM> companies;
		try {
			companies = require.getAllCompany();
		} catch (SQLException e) {
			LogManager.err(e);
			return false;
		}

		int companyCount = 1;
		for (JinKaisyaM company : companies) {
			LogManager.out("%4d/%4d 会社\r\n", companyCount, companies.size());
			List<JmKihon> employees;
			try {
				employees = require.getAllEmployee(company.getCompanyCode());
			} catch (SQLException e) {
				LogManager.err(e);
				return false;
			}

			int employeeCount = 0;
			LogManager.out("  0 ％の処理が完了しました。");
			for (JmKihon employee : employees) {

				String plainPassword = employee.getPassword();
				if(plainPassword == null || plainPassword.isEmpty()) {
					continue;
				}

				// ユーザIDを新規発行
				String userId = UUID.randomUUID().toString();

				String newPassHash = PasswordHash.generate(plainPassword, userId);

				try {
					require.save(newPassHash, employee, userId);
				} catch (SQLException e) {
					LogManager.err(e);
					return false;
				}
				employeeCount++;
				LogManager.out("\r %2d", Math.round(employeeCount/employees.size()) * 100);
			}
			companyCount++;
			LogManager.out("\r %2d", 100);
			LogManager.out("\r\n");
		}
		LogManager.out("アカウントの移行 -- 正常終了 --");

		return true;
	}

	public interface Require {
		List<JinKaisyaM> getAllCompany() throws SQLException;
		List<JmKihon> getAllEmployee(int companyCode) throws SQLException;
		void truncateMappingTable() throws SQLException;
		void save(String password, JmKihon employee, String userId) throws SQLException;
	}
}
