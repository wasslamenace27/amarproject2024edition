package com.example.newessai;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import helper.ItemsDomain;
import helper.ManagmentCart;

public class DetailActivity extends BaseActivity {
    Activitydetailbinding binding;
    private itemsDomain object;
    private int numberOrder=1;
    private ManagmentCart managmentCart;
    private Handler SlideHandle= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Activitydetailbinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        getBundles();
        initbanners();
        initSize();
        setupViewPager();
    }

        private void initSize() {
            ArrayList<String> list = new ArrayList<>();
            list.add("S");
            list.add("M");
            list.add("L");
            list.add("XL");
            list.add("XXL");

            binding.recyclerSize.setAdapter(new SizeAdapter(list));
            binding.recyclerSize.setLayoutManager(new LinearLayoutManager(context: this, LinearLayoutManager.HORIZONTAL, reverseLayout: false));
        }

    }

    private void initbanners() {
        List<SliderItems> sliderItems = new ArrayList<>();

        for (int i = 0; i < object.getPicUrl().size(); i++) {
            sliderItems.add(new SliderItems(object.getPicUrl().get(i)));
        }

        binding.viewpageSlider.setAdapter(new SliderAdapter(sliderItems, binding.viewpageSlider));
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(3);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundles() {
        Object=(ItemsDomain) getIntent().getSerizableExtra9("object");
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("$"+object.getPrice());
        binding.ratingBar.setRating(object.getRating());
        binding.ratingtxt.setText(object.getRating()+"Rating");
        binding.addTocatrtBtn.setOnClickListener(new View.OnClickListener()){
            @Override
                    public void onClick(View v){
                object.setNumberInCart(numberOrder);
                managmentCart.insertItem(object)
            }
        }
        binding.backBtn.setOnClickListener(new View.OnClickListener()){
            @Override
            public void onClick(View v){
                finish();
            }
        }
    }
    private void setupViewPager() {

        ViewPagerAdapater adapter = new ViewPagerAdapater(getSupportFragmentManager());
        DescriptionFragment tab1 = new DescriptionFragment();
        ReviewFragment tab2 = new ReviewFragment();
        SoldFragment tab3 = new SoldFragment();

        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();

        bundle1.putString("description", object.getDescription());

        tab1.setArguments(bundle1);
        tab2.setArguments(bundle2);
        tab3.setArguments(bundle3);

        adapter.addFrag(tab1, title: "Descriptions");
        adapter.addFrag(tab2, title: "Reviews");
        adapter.addFrag(tab3, title: "Sold");

        binding.viewpager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
    private void addFrag(Fragment fragment, String title) {
        mFragmentTitleList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}