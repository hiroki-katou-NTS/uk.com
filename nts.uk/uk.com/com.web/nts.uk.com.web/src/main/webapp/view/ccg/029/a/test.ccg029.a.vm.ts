module nts.uk.hr.view.ccg029.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {

        input: Input;
        
        constructor() {
            var self = this;
            //param
            self.input = new Input();

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        public seletedEmployee(data): void{
            console.log(data);
        }
    }
    
    class Input {
        systemType: number; //システム区分（0：共通、1：就業、2：給与、3：人事）
        includePreEmployee: boolean; //入社前社員を含める
        includeRetirement: boolean; //退職者を含める
        includeAbsence: boolean; //休職者を含める 
        includeClosed: boolean; //休業者を含める
        includeTransferEmployee: boolean; //出向社員を含める
        includeAcceptanceTransferEmployee: boolean; //受入出向社員を含める
        getPosition: boolean; //職位を取得する
        getEmployment: boolean; //雇用を取得する
        getPersonalFileManagert: boolean; //個人ファイル管理を取得する
        
        constructor(input: any) {
            this.systemType = input ? input.systemType || 1 : 1;
            this.includePreEmployee = input ? input.includePreEmployee || true: true;
            this.includeRetirement = input ? input.includeRetirement || true: true;
            this.includeAbsence = input ? input.includeAbsence || true: true;
            this.includeClosed = input ? input.includeClosed || true: true;
            this.includeTransferEmployee = input ? input.includeTransferEmployee || true: true;
            this.includeAcceptanceTransferEmployee = input ? input.includeAcceptanceTransferEmployee || true: true;
            this.getPosition = input ? input.getPosition || true: true;
            this.getEmployment = input ? input.getEmployment || true: true;
            this.getPersonalFileManagert = input ? input.getPersonalFileManagert || true: true;
        }
    }
}
