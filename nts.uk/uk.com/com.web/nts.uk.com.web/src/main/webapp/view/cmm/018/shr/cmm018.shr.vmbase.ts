module nts.uk.com.view.cmm018.shr {
    export module vmbase {
        export class GridPersonCostCalculation {
            dateRange: string;  
            constructor(dateRange: string) {
                var self = this;
                self.dateRange = dateRange;
            }  
        }
    
        
        export class ProcessHandler {
            
            /**
             * get one day before input date as string format
             */
            static getOneDayBefore(date: string) {
                return moment(date).add(-1,'days').format("YYYY/MM/DD");
            }
            
            /**
             * get one day after input date as string format
             */
            static getOneDayAfter(date: string) {
                return moment(date).add(1,'days').format("YYYY/MM/DD");
            }
            
            /**
             * check input date in range, if date in range return true
             */
            static validateDateRange(inputDate: string, startDate: string, endDate: string){
                return moment(inputDate).isBetween(moment(this.getOneDayBefore(startDate)), moment(this.getOneDayAfter(endDate)));
            }
            
            /**
             * check input date before or equal date
             */
            static validateDateInput(inputDate: string, date: string){
                return moment(inputDate).isSameOrAfter(moment(date));
            }
        }
        export enum MSG {
            MSG015 = <any>"登録しました。",
            MSG018 = <any>"選択中のデータを削除しますか？",
            MSG065 = <any>"最新の履歴の有効開始日より以前の有効開始日を登録できません。",
            MSG066 = <any>"割増項目が設定されてません。",
            MSG102 = <any>"最新の履歴開始日以前に履歴を追加することはできません。",
            MSG128 = <any>"最後の履歴を削除することができません。"
        }
        export class Approver{
            id: string;
            code: string;
            name: string;
            constructor(id: string, code: string, name: string){
                this.id = id;
                this.code = code;
                this.name = name;    
            }
        } 
    }
}