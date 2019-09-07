<template>
<div class="kdws03d">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close()">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div><span>{{ params.employeeName }}</span></div>
    </div>
  </div>  
  <div class="row uk-bg-light-coral">
    {{'KDWS03_40' | i18n}}    
  </div>  
  <fix-table table-class="table table-bordered m-0 table-sm" :rowNumber="6" class="border-bottom mx-n2" style="font-size: 11px">
    <thead class="uk-bg-headline">
        <tr>
            <th>{{'KDWS03_41' | i18n}}</th>
            <th>{{'KDWS03_42' | i18n}}</th>
            <th>{{'KDWS03_43' | i18n}}</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>{{'KDWS03_41' | i18n}}</td>
            <td>{{'KDWS03_42' | i18n}}</td>
            <td>{{'KDWS03_43' | i18n}}</td>
        </tr>
    </tbody>
  </fix-table>
</div>
</template>