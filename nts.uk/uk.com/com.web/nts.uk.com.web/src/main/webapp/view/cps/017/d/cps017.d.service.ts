module nts.uk.com.view.cps017.d.service {

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
