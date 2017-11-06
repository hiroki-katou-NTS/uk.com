module nts.uk.com.view.cps017.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        addHistorySelectionData: "ctx/bs/person/info/setting/selection/addHistoryData"
    }

    export function addHistorySelectionData(command) {
        return ajax(paths.addHistorySelectionData, command);
    }


}






/*
module nts.uk.com.view.cps017.c.service {

    export function getItems() {
        return $.Deferred().resolve(new DemoData().items).promise();
    }

    export function getItem(id: string) {
        let items = new DemoData().items,
            item = _.find(items, x => x.id == id);

        return $.Deferred().resolve(item).promise();
    }

    class DemoData {
        items = [{
            id: 1,
            name: '個人情報選択項目の定義'
        }];
    }

}
*/
