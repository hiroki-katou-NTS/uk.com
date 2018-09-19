module nts.uk.pr.view.qmm012.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    export module service {
        
        let paths = {
            getAllStatementItemData: "ctx/pr/core/wageprovision/statementitem/getAllStatementItemData/{0}/{1}",
            registerStatementItemData: "ctx/pr/core/wageprovision/statementitem/registerStatementItemData",
            removeStatementItemData: "ctx/pr/core/wageprovision/statementitem/removeStatementItemData"
        }
        
        export function getAllStatementItemData(categoryAtr: number, isdisplayAbolition: boolean): JQueryPromise<any> {
            let _path = format(paths.getAllStatementItemData, categoryAtr, isdisplayAbolition);
            return ajax('pr', _path);
        }
        
        export function registerStatementItemData(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.registerStatementItemData, command);
        }
        
        export function removeStatementItemData(command: any) : JQueryPromise<any> {
            return nts.uk.request.ajax('pr', paths.removeStatementItemData, command);
        }
    }
}
