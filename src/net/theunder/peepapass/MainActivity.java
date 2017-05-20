package net.theunder.peepapass;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.TimelineManager;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class MainActivity extends Activity {
	private List<Card> mCards;					/// pass cards
	private List<Card> vCards;					/// voice cards
	private List<Card> bCards;					/// batch of pass cards
    private CardScrollView mCardScrollView;
    private long cardId = -1L;
/// TODO: @COMEBACK WHEN Batch is pulled from query.db
//    	strPwd= "x123456789a123456789b123456789c123456789d";		        // 41 char pwd
//    	strUser= "1234567890123456789012345678901234567890@email.com";		// 40 char username
//    	strSite= "http://domain.web-site.com/accounts";						// 36 char website
//		 createCardBatch(strSite, strUser, strPwd);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createCards();
        mCardScrollView = new CardScrollView(this);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


    private void createCards() {
    	mCards = new ArrayList<Card>();
        Card card;
        card = new Card(this);
        card.setText("Username @ www.website.com");		// text for password
        card.setFootnote("Password");		// footer with username @ website
        card.setImageLayout(Card.ImageLayout.FULL);					// full layout
        card.addImage(R.drawable.website);							// image of website
        mCards.add(card);
        TimelineManager tm = TimelineManager.from(this);
    }
    
    private void createCardBatch(String sSite, String sUser, String sPwd){
    	bCards = new ArrayList<Card>();
    	Card bcard;    	
    	///card 1
    	bcard = new Card(this);
    	bcard.setText("Username:\n" + sUser + "\nPassword:\n" + sPwd);		// forty char pwd
    	bcard.setFootnote(sSite);		// footer with username @ website
    	bCards.add(bcard);
        TimelineManager tm = TimelineManager.from(this);
	    cardId = tm.insert(bcard);
    }
	private class ExampleCardScrollAdapter extends CardScrollAdapter {
        @Override
        public int findIdPosition(Object id) {
            return -1;
        }
        @Override
        public int findItemPosition(Object item) {
            return mCards.indexOf(item);
        }
        @Override
        public int getCount() {
            return mCards.size();
        }
        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).toView();
        }
    }
}
