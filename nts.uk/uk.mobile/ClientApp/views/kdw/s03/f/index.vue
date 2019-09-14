<template>
<div class="kdws03f">
    <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close()">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div class="col-6 pl-0 pr-2"><span>{{ empName }}</span></div>
      <div class="col-5 pl-0 pr-0"><span>{{ date  | yearmonth('YYYY年MM月')}}</span></div>
    </div>
  </div>
    <table class="table table-bordered">
    <tbody>
      <tr v-for="month in monthData" v-bind:key="month.id" :value="month">
        <td class="pl-0 pr-0 text-center uk-bg-green">{{ month.name}}</td>
        <td>
          <div class="row">
              <div class="col-10 d-inline-block">{{ month.value}}</div>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</div>
</template>