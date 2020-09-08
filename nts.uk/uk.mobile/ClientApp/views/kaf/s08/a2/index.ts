import { moment, Vue, DirectiveBinding } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { StepwizardComponent } from '@app/components';
import { FixTableComponent } from '@app/components/fix-table';
import {KafS08DComponent} from '../../../kaf/s08/d'; 

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
    props : {
        derpartureTime : {
            type : Number
        },
        returnTime:{
            type : Number
        }
    },
    directives : {
        date : {
            bind(el: HTMLElement, binding: DirectiveBinding) {
                const mm = moment(binding.value, 'YYYY/MM/DD');
                el.innerHTML = mm.format('MM/DD(ddd)');
                el.className = mm.clone().locale('en').format('dddd').toLocaleLowerCase();
            }
        }
    },
    constraints: [],
})
export class KafS08A2Component extends Vue {
    @Prop({default : () => ({})})
    //public params: string = 'KafS08A2';
    //public readonly params!: any;
    public name: string = 'hello my dialog';
    public date: Date = new Date(2020,2,14);
    public mtable = require('./mock_data.json');
    //public readonly paramsA2!: IPrams;

    public showModal(type) {
        const vm = this;
        let name = this.name;
        this.$modal(KafS08DComponent, {  timetowork: vm.mtable.timetowork, leavetime: vm.mtable.leavetime, day : vm.mtable.date} )
        .then((data) => {
            console.log(data);
        });
    }

    //nhảy đến step three
    public nextToStepThree() {
        const vm = this;
        this.$emit('nextToStepThree');
    }

    //quay trở lại step one
    public prevStepOne() {
        this.$emit('prevStepOne',{});
    }

}

const API = {
    checkBeforeApply : 'at/request/application/businesstrip/mobile/checkBeforeRegister',
};



