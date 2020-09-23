<template>
  <div class="cmms45componentsapp3">
    <!-- B5_1 -->
    <div class="card mx-n4 py-2">
      <div class="card-header">{{'KAFS08_93' | i18n}}</div>
    </div>
    <!-- B5_2 -->
    <div class="px-3 py-2">
      <span>{{'KAFS08_94' | i18n}}</span>
      <span class="pl-5" v-if="time.departureTime == null">{{''}}</span>
      <span class="pl-5" v-else>{{time.departureTime | timewd}}</span>
    </div>
    <div class="px-3 py-2">
      <span>{{'KAFS08_95' | i18n}}</span>
      <span class="pl-5" v-if="time.departureTime == null">{{''}}</span>
      <span class="pl-5" v-else>{{time.returnTime | timewd}}</span>
    </div>
    <!-- B10 -->
      <table class="table uk-table-striped table-bordered">
        <thead>
          <tr>
            <th class="px-1" scope="col">{{"KAFS08_97" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_98" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_99" | i18n}}</th>
            <th class="px-1" scope="col">{{"KAFS08_100" | i18n}}</th>
            <th class="px-1 border-right-0" scope="col">{{"KAFS08_101" | i18n}}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data,index) in table" v-bind:key="index">
            <td class="px-1" v-date="data.date">{{data.date}}</td>
            <td class="px-1">{{data.wkTimeCd}}</td>
            <td class="px-1">{{data.wkTypeCd}}</td>
            <td class="px-1">{{data.startWorkTime | timewd}}</td>
            <td class="px-1">{{data.endWorkTime | timewd}}</td>
          </tr>
        </tbody>
      </table>
  </div>
</template>