module nts.uk.com.view.cps016.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllSelectionItems: "ctx/bs/person/info/setting/selection/findAll",
    }

    export function getAllSelectionItems() {
        return ajax(paths.getAllSelectionItems);
    }





    export function getItems() {
        return $.Deferred().resolve(new DemoData().listItems).promise();
    }

    export function getItem(id: string) {
        let listItems = new DemoData().listItems,
            item = _.find(listItems, x => x.id == id);

        return $.Deferred().resolve(item).promise();
    }

    class DemoData {
        listItems = [{
            id: 1,
            name: '個人情報選択項目の定義',
            numberCodeDigits: 1,
            numberDigits: 10,
            numberExternalCodeDigits: 20,
            integrationCode: 1,
            share: 2,
            enable: 1,
            notes: 'FFDFDGFdsfgdgfdg...'
        },
            {
                id: 2,
                name: '画面状態遷移図',
                numberCodeDigits: 2,
                numberDigits: 10,
                numberExternalCodeDigits: 30,
                integrationCode: 1,
                share: 3,
                enable: 0,
                notes: 'Shits...'
            },
            {
                id: 3,
                name: 'モーダル画面起動中',
                numberCodeDigits: 1,
                numberDigits: 20,
                numberExternalCodeDigits: 40,
                integrationCode: 1,
                share: 1,
                enable: 1,
                notes: 'モーダル画面起動中...'
            }
        ];
    }
}