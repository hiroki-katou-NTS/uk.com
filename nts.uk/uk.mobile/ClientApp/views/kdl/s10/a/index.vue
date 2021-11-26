<template>
  <div class="kdls10 modal-body p-0">
    <div class="modal-header rounded-0 d-block p-0">
      <!-- A1_1 -->
      <div class="uk-bg-teal p-2">
        <h4 class="modal-title text-white">
          <i class="fas fa-angle-double-left mr-1" v-on:click="$close()"></i>
          <span>{{ "KDLS10_1" | i18n }}</span>
        </h4>
      </div>
      <!-- A1_2 -->
      <div class="uk-bg-headline p-2 pb-1">
        <label>{{ "KDLS10_2" | i18n }}</label>
        <!-- A1_3,A1_4 -->
        <nts-search-box
          class="bg-grey-100"
          placeholder="KDLS10_3"
          v-model="searchText"
          v-on:search="searchList"
        />
      </div>
    </div>
    <!-- A1_5,A1_6,A1_7 -->
    <div>
      <ul class="list-group list-group-single-select">
        <li class="list-group-item" v-for="(item, k) of data" v-bind:key="k" :class=" item.workLocationCD == params.selectedCode || (item.workLocationCD == '' && activeNoSelect) ? 'active' : '' " v-on:click="$close(item)">
         <div class="text-truncate mr-3">
           <span v-if="item.contractCode != '-1'">{{ item.workLocationCD }}<span class="text-distance">{{ item.workLocationName}}</span></span>
           <span v-if="item.contractCode == '-1'">{{ item.workLocationCD }}<span>{{ item.workLocationName}}</span></span>
         </div>
        </li>
      </ul>
    </div>
  </div>
</template>
