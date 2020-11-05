/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal013 {
  export module common {
    /**
     * Workplaces Category
     * @returns  Array
     */
    export function workplaceCategory() {
      return [
        { code: WorkplaceCategory.MASTER_CHECK_BASIC, name: 'マスタチェック(基本)' },
        { code: WorkplaceCategory.MASTER_CHECK_WORKPLACE, name: 'マスタチェック(職場)' },
        { code: WorkplaceCategory.MASTER_CHECK_DAILY, name: 'マスタチェック(基本)' },
        { code: WorkplaceCategory.SCHEDULE_DAILY, name: 'スケジュール／日次' },
        { code: WorkplaceCategory.MONTHLY, name: '月次' },
        { code: WorkplaceCategory.APPLICATION_APPROVAL, name: '申請承認' },
      ];
    }

    export enum WorkplaceCategory {
      MASTER_CHECK_BASIC = 0,// マスタチェック(基本)    
      MASTER_CHECK_WORKPLACE = 1,// マスタチェック(職場)    
      MASTER_CHECK_DAILY = 2,// マスタチェック(日次)
      SCHEDULE_DAILY = 3, // "スケジュール／日次",    
      MONTHLY = 4,// 月次    
      APPLICATION_APPROVAL = 5, //"申請承認"
    }

    export class Alarm {
      code: string;
      name: string;
      constructor(code?: string, name?: string) {
        this.code = code;
        this.name = name;
      }
    }
  
    export class Category {
      code: number;
      name: string;
      constructor(code?: number, name?: string) {
        this.code = code;
        this.name = name;
      }
    }
  
    export class AlarmDto {
      isChecked: KnockoutObservable<boolean> = ko.observable(false);
      register: KnockoutObservable<boolean> = ko.observable(false);
      name: KnockoutObservable<string> = ko.observable(null);
      message: KnockoutObservable<string> = ko.observable(null);
      constructor(isChecked?: boolean, register?: boolean, name?: string, message?: string) {
        this.isChecked(isChecked);
        this.register(register);
        this.name(name);
        this.message(message);
      }
    }
    
  }
}