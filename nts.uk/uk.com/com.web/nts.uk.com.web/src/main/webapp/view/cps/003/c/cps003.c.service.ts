module nts.uk.com.view.cps003.c.service {
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
            columnSetting: '選択項目',
            notes: '個人情報選択項目の定義'

        },
            {
                id: 2,
                name: '画面状態遷移図',
                columnSetting: '状態遷移図項目',
                notes: '画面状態遷移図'
            },
            {
                id: 3,
                name: 'モーダル画面起動中',
                columnSetting: '画面起動中項目',
                notes: 'モーダル画面起動中'
            }
        ];
    }
}
