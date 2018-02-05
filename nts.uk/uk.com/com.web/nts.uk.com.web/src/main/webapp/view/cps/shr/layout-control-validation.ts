module nts.layout {
    import ajax = nts.uk.request.ajax;

    export const validate = {
        removeDoubleLine: (items: Array<any>) => {
            let maps = _(items)
                .map((x, i) => (x.layoutItemType == IT_CLA_TYPE.SPER) ? i : -1)
                .filter(x => x != -1).value();

            _.each(maps, (t, i) => {
                if (maps[i + 1] == t + 1) {
                    _.remove(items, (m: any) => {
                        let item: any = ko.unwrap(items)[maps[i + 1]];
                        return item && item.layoutItemType == IT_CLA_TYPE.SPER && item.layoutID == m.layoutID;
                    });
                }
            });
        }
    }

    export const constraint = (lstCls: Array<any>) => {
        let self = {
            _lst: lstCls,
            lst: (lstCls?: Array<any>) => {
                if (_.isArray(lstCls)) {
                    self._lst = lstCls;
                }

                return self._lst;
            },
            combobox: (categoryCode: string, subscribeCode: string, targetCode: string) => {
                let controls = _(self.lst()).map(x => x.items()).flatten().flatten().value(),
                    target = _.find(controls, (x: any) => x.categoryCode == categoryCode && x.itemCode == targetCode),
                    subscribe = _.find(controls, (x: any) => x.categoryCode == categoryCode && x.itemCode == subscribeCode);

                debugger;
                fetch.combobox("", "");
            }
        };

        self.lst(lstCls);

        return self;
    }

    const fetch = {
        combobox: (baseDate: string, itemCode: string) => ajax(`/pereg/get-combo-value/${itemCode}/${baseDate}`)
    }


    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }
} 