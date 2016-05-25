package dvlps.memorygame;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class menu extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Button btn_1player, btn_2players;

    MediaPlayer mainMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mainMusic = MediaPlayer.create(this, R.raw.memory_game_music);
        mainMusic.start();
        mainMusic.setLooping(true);

        btn_1player = (Button) findViewById(R.id.btn_1player);
        btn_2players = (Button) findViewById(R.id.btn_2players);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        btn_1player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixSolo();
            }
        });

        btn_2players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choixMulti();
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Information";
                break;
            case 2:
                mTitle = "Score";
                break;
            case 3:
                mTitle = "Option";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((menu) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        mainMusic.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainMusic.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mainMusic.start();
        mainMusic.setLooping(true);
    }

    public void choixSolo() {
        mainMusic.stop();
        Intent intent = new Intent(menu.this, menuNiveau.class);
        startActivity(intent);
    }

    public void choixMulti() {
        mainMusic.stop();
        Intent intent = new Intent(menu.this, menuMulti.class);
        startActivity(intent);
    }
}
