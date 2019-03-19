import { Vue } from '@app/provider';
import { component } from '@app/core/component';

@component({
    route: '/components/time-editor',
    template: require('./index.html')
})
export class TestTimeEditor extends Vue {

    minutes: string| number = 600;
    
}
