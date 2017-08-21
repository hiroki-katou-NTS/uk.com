module nts.uk.com.view.cas001.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllPersonRole: "ctx/bs/person/roles/findAll",
        update: "ctx/bs/person/roles/auth/update"
    }
    /**
     * Get All Person Role
     */
    export function getAllPersonRole(): JQueryPromise<Array<any>> {
        return ajax(paths.getAllPersonRole);
    }

    /**
  *update Person Role
  */
    export function update(object: any): JQueryPromise<any> {
        return ajax(paths.update, object);
    }
}
