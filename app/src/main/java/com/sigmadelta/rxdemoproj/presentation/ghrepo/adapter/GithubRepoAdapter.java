package com.sigmadelta.rxdemoproj.presentation.ghrepo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sigmadelta.rxdemoproj.R;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepo;
import com.sigmadelta.rxdemoproj.domain.ghrepo.GithubRepoApi;

import java.util.EnumMap;
import java.util.List;


public class GithubRepoAdapter extends RecyclerView.Adapter<GithubRepoAdapter.GithubRepoViewHolder> {

    private List<GithubRepo> _repoList;

    public GithubRepoAdapter(List<GithubRepo> repoList) {
        _repoList = repoList;
    }

    @Override
    public GithubRepoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new GithubRepoViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.repo_list_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(GithubRepoViewHolder githubRepoViewHolder, int i) {
        GithubRepo repo = _repoList.get(i);
        githubRepoViewHolder.repoTxtViewMap.get(GithubRepoApi.NAME).setText(repo.getName());
        githubRepoViewHolder.repoTxtViewMap.get(GithubRepoApi.STARS).setText(String.valueOf(repo.getStars()));
        githubRepoViewHolder.repoTxtViewMap.get(GithubRepoApi.ISSUE_COUNT).setText(String.valueOf(repo.getIssues()));

        githubRepoViewHolder.imgFork.setVisibility(
                repo.getFork() ? View.VISIBLE : View.INVISIBLE
        );
    }

    @Override
    public int getItemCount() {
        return _repoList.size();
    }

    class GithubRepoViewHolder extends RecyclerView.ViewHolder {
        EnumMap<GithubRepoApi, TextView> repoTxtViewMap = new EnumMap(GithubRepoApi.class);
        ImageView imgFork;

        GithubRepoViewHolder(View view) {
            super(view);
            repoTxtViewMap.put(GithubRepoApi.NAME, (TextView) view.findViewById(R.id.txt_repo_name));
            repoTxtViewMap.put(GithubRepoApi.STARS, (TextView) view.findViewById(R.id.txt_repo_stars));
            repoTxtViewMap.put(GithubRepoApi.ISSUE_COUNT, (TextView) view.findViewById(R.id.txt_repo_issue_count));
            imgFork = (ImageView) view.findViewById(R.id.img_repo_fork);
        }
    }
}
