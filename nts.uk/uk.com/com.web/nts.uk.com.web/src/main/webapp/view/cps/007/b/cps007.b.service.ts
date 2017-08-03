import ajax = nts.uk.request.ajax;
import format = nts.uk.text.format;

module cps007.b.service {
    let paths: any = {
        'getCat': '/{0}',
        'getItemDs': '/{0}'
    };

    export function getCategory(cid) {
        //return ajax(format(paths.getCat, cid));
        return $.Deferred().resolve({ id: 'ID1', code: 'COD1', name: '家族情報' }).promise();
    }

    export function getItemDefinitions(cid) {
        let data = [];
        for (let i = 0; i <= 30; i++) {
            data.push({ id: "ID" + i, code: "COD" + i, name: "Name " + i });
        }
        return $.Deferred().resolve(data).promise();
        //return ajax(format(paths.getItemDs, cid));
    }
}