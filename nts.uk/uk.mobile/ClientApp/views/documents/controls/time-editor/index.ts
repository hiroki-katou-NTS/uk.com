import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: {
        url: '/controls/time-input',
        parent: '/documents'
    },
    template: require('./index.html')
})
export class TestTimeEditor extends Vue {

    minutes: string| number = 600;
    
}