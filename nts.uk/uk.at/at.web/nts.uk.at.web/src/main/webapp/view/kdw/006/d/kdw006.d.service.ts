module nts.uk.at.view.kdw006.d.service {
    let paths: any = {
        getRoles: 'at/record/workrecord/authfuncrest/find-emp-roles',
        findFuncRest: 'at/record/workrecord/authfuncrest/find/',
        register: 'at/record/workrecord/authfuncrest/register'
    }

    export function getRoleList(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getRoles);
    }

    export function findFuncRest(roleId: String): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.findFuncRest + roleId);
    }

    export function register(roleId: number, functionalRestriction: any): JQueryPromise<Array<any>> {
        let command = {
            roleId : roleId,
            authFuncRests : functionalRestriction
        };
        return nts.uk.request.ajax("at", paths.register, command);
    }


}
