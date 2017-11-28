module nts.uk.com.view.cmm007.e {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findList: "at/shared/overtimeworkframe/findAll",
                save: "at/shared/overtimeworkframe/save",
            };
        
        /**
         * 
         */
        export function findListOvertimeWorkFrame(): JQueryPromise<Array<model.OvertimeWorkFrameDto>>{
            return nts.uk.request.ajax("at", path.findList);
        }
        
        /**
         * 
         */
        export function saveOvertimeWorkFrame(data: model.OvertimeWorkFrameSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save, data);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
       export class OvertimeWorkFrameDto {
            overtimeWorkFrNo: number;
            overtimeWorkFrName: string;
            transferFrName: string;
            useAtr: number;
            
            constructor(no: number, name: string, transName:string, useAtr: number){
                this.overtimeWorkFrNo = no;
                this.overtimeWorkFrName = name;
                this.transferFrName = transName;
                this.useAtr = useAtr;
            }
        }
        
        export class OvertimeWorkFrameSaveCommand {
            listData: Array<model.OvertimeWorkFrameDto>;

            constructor(data: Array<model.OvertimeWorkFrameDto>){
               this.listData = data;
            }
        }
    }
    
}