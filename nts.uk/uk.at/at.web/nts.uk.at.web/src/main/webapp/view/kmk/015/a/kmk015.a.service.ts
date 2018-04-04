module nts.uk.at.view.kmk015.a {
    export module service {
        
        let servicePath: any = {
            findListWorkType: 'at/share/worktype/findAll'
        };
        
        export function findListWorkType(): JQueryPromise<Array<model.WorkType>> {
            return nts.uk.request.ajax(servicePath.findListWorkType);
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
        }
    }
}