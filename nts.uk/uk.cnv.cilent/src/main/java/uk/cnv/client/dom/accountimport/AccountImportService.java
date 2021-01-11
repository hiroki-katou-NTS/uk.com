package uk.cnv.client.dom.accountimport;

import java.util.List;
import java.util.UUID;

import nts.gul.security.hash.password.PasswordHash;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmKihon;

public class AccountImportService {

	public boolean doWork(Require require) {
		System.out.println("アカウントの移行 -- 処理開始 --");

		List<JinKaisyaM> companies = require.getAllCompany();

		int companyCount = 1;
		for (JinKaisyaM company : companies) {
			System.out.printf("%4d/%4d 会社\r\n", companyCount, companies.size());
			List<JmKihon> employees = require.getAllEmployee(company.getCompanyCode());

			int employeeCount = 1;
			System.out.print("  0 ％の処理が完了しました。");
			for (JmKihon employee : employees) {
				System.out.printf("\r %2d", Math.round(employeeCount/employees.size()) * 100);

				String plainPassword = employee.getPassword();
				// ユーザIDを新規発行
				String userId = UUID.randomUUID().toString();

				String newPassHash = PasswordHash.generate(plainPassword, userId);

				require.save(newPassHash, employee, userId);
				employeeCount++;
			}
			companyCount++;
		}
		System.out.print("\r\n");
		System.out.println("アカウントの移行 -- 正常終了 --");
		return true;
	}

	public interface Require {
		List<JinKaisyaM> getAllCompany();
		List<JmKihon> getAllEmployee(int companyCode);
		void save(String password, JmKihon employee, String userId);
	}
}
