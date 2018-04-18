module nts.uk.at.view.kmk015.a {
    export module service {
        
        let servicePath: any = {
            findListWorkType: 'at/share/worktype/findWorkTypeByCondition',
            getHistoryByWorkType: 'at/request/application/vacation/getHistoryByWorkType'
        };
        
        export function findListWorkType(): JQueryPromise<Array<model.WorkType>> {
            return nts.uk.request.ajax(servicePath.findListWorkType);
        }
        
        export function getHistoryByWorkType(workTypeCode : string): JQueryPromise<Array<model.History>> {
            console.log(servicePath.getHistoryByWorkType);
            return nts.uk.request.ajax(servicePath.getHistoryByWorkType+ '/' + workTypeCode);
        }
        
        export module model {
            export class WorkType {
                abbreviationName: string;
                companyId: string;
                displayAtr: number;
                memo: string;
                name: string;
                sortOrder: number;
                symbolicName: string;
                workTypeCode: string;
                abolishAtr: number;
            }
            export class History {
                time: string;
            }
        }
    }
}