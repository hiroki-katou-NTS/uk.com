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
    slots = [
        {
            flex: 1,
            values: ['2015-01', '2015-02', '2015-03', '2015-04', '2015-05', '2015-06'],
            className: 'slot1',
            textAlign: 'right'
        }, {
            divider: true,
            content: '-',
            className: 'slot2'
        }, {
            flex: 1,
            values: ['2015-01', '2015-02', '2015-03', '2015-04', '2015-05', '2015-06'],
            className: 'slot3',
            textAlign: 'left'
        }
    ];

    onValuesChange(picker, values) {
        if (values[0] > values[1]) {
            picker.setSlotValue(1, values[0]);
        }
    }
}