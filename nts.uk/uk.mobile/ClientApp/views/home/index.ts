import { Vue } from '@app/provider';
import { component } from '@app/core/component';
@component({
    route: '/',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: []
})
export class HomeComponent extends Vue {
    selecteds = {
        year: 2019,
        month: 1,
        day: 2
    };

    dataSources = {
        year: [],
        month: [],
        day: []
    };

    created() {
        for (var i = 1900; i <= 2099; i++) {
            this.dataSources.year.push({ text: `${i}年`, value: i });
        }

        for (var i = 1; i <= 12; i++) {
            this.dataSources.month.push({ text: `${i}`, value: i });
        }

        for (var i = 1; i <= 31; i++) {
            this.dataSources.day.push({ text: `${i}`, value: i });
        }

        window['vm'] = this;
    }

    showPicker() {
        let onSelect = function (selects: any, pkr: { dataSources: { day: any[] } }) {
            pkr.dataSources.day = [];

            if (selects.month === 2) {
                for (var i = 1; i <= 28; i++) {
                    pkr.dataSources.day.push({ text: `${i}`, value: i });
                }
            } else {
                for (var i = 1; i <= 31; i++) {
                    pkr.dataSources.day.push({ text: `${i}`, value: i });
                }
            }
        };

        this.$picker(this.selecteds, this.dataSources, { title: 'home', onSelect })
            .then((v: any) => {
                if (v !== undefined) {
                    this.selecteds = v;
                }
            })
    }
}