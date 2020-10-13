module nts.uk.at.view.ksu001.q {
	export module service {
		var paths = {
			findExtBudget: "at/schedule/budget/external/infor/findExternalBudget",
			findBudgetDaily: "at/schedule/budget/external/infor/findBudgetDaily",
			register: "at/schedule/budget/external/infor/register"
		}

		export function findExtBudget(param: any): JQueryPromise<model.InitialStartupScreenResultDto> {
			return nts.uk.request.ajax("at", paths.findExtBudget, param);
		}

		export function findBudgetDaily(param: any): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.findBudgetDaily, param);
		}

		export function register(param: any): JQueryPromise<any> {
			return nts.uk.request.ajax("at", paths.register, param);
		}

		export module model {
			export class InitialStartupScreenResultDto {
				orgName: string;
				externalBudgetItems: any;

				constructor(orgName: string, externalBudgetItems: any) {
					this.orgName = orgName;
					this.externalBudgetItems = externalBudgetItems;
				}
			}
		}
	}
}