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
        this.$picker(this.selecteds, this.dataSources, { title: 'home' })
            .then((v: any) => {
                if (v !== undefined) {
                    this.selecteds = v;
                }
            })
    }
}