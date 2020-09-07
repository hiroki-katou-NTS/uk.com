import { Vue, DirectiveBinding } from '@app/provider';
import { component } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { FixTableComponent } from '@app/components/fix-table';
import {KafS08DComponent} from '../../../kaf/s08/d';
import * as moment from 'moment';

// import abc from './mock_data.json';

@component({
    name: 'kafs08a2',
    route: '/kaf/s08/a2',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components: {
        'step-wizard': StepwizardComponent,
        'fix-table': FixTableComponent
    },
    props: {
        departureTime : {
            type : Number
        },
        returnTime : {
            type : Number
        }
    },
    directives : {
        date : {
            bind(el: HTMLElement, binding: DirectiveBinding) {
                const mm = moment(binding.value);
                el.innerHTML = mm.format('MM/DD(ddd)');
                el.className = mm.clone().locale('en').format('dddd').toLocaleLowerCase();
            }
        }
    },
    constraints: [],
})


export class KafS08A2Component extends Vue {
    public title: string = 'KafS08A2';
    public name: string = 'hello my dialog';
    public date: Date = new Date(2020,2,14);

    public mtable = require('./mock_data.json');

    public step = 'KAFS08_11';

    public showModal(type) {
        let name = this.name;
        this.$modal(KafS08DComponent, {  time1: 120, time2: 188 } )
        .then((data) => {
            console.log(data);
        });
    }
}